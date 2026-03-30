package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.StudyMaterialDto
import com.studyswap.mobile.app.data.source.remote.model.toApiDouble
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.utils.computeOtpDeadlineWallClockMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMarketplaceItemDetailUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataStore: AppPreferenceDataStore
) {

    private val _uiDataStateFlow = MutableStateFlow(MarketplaceItemDetailUiDataState())

    operator fun invoke(
        itemId: String,
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): MarketplaceItemDetailUiState = MarketplaceItemDetailUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(itemId, context, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        itemId: String,
        context: Context,
        event: MarketplaceItemDetailUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            MarketplaceItemDetailUiEvent.Load -> coroutineScope.launch {
                val idInt = itemId.toIntOrNull()
                if (idInt == null) {
                    _uiDataStateFlow.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Invalid material id",
                            item = buildSample(itemId)
                        )
                    }
                    return@launch
                }
                val uid = dataStore.getUserData()?.id
                _uiDataStateFlow.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                        currentUserId = uid,
                        hasRedeemedUnlock = false,
                        authorOtpCode = null,
                        authorOtpDeadlineWallMs = null,
                        buyerOtpInput = ""
                    )
                }
                apiRepository.getMaterial(idInt).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> Unit
                        is NetworkResult.Success -> {
                            val dto = result.data?.data
                            if (dto != null) {
                                _uiDataStateFlow.update {
                                    it.copy(
                                        item = dto.toDetail(itemId),
                                        isLoading = false,
                                        currentUserId = uid
                                    )
                                }
                            } else {
                                _uiDataStateFlow.update {
                                    it.copy(
                                        item = buildSample(itemId),
                                        isLoading = false,
                                        errorMessage = "Material not found."
                                    )
                                }
                            }
                        }
                        is NetworkResult.Error -> {
                            _uiDataStateFlow.update {
                                it.copy(
                                    item = buildSample(itemId),
                                    isLoading = false,
                                    errorMessage = result.message
                                )
                            }
                        }
                        is NetworkResult.UnAuthenticated -> {
                            _uiDataStateFlow.update {
                                it.copy(item = buildSample(itemId), isLoading = false)
                            }
                        }
                    }
                }
            }
            MarketplaceItemDetailUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            MarketplaceItemDetailUiEvent.OnBookmarkToggle ->
                _uiDataStateFlow.update { it.copy(isBookmarked = !it.isBookmarked) }
            MarketplaceItemDetailUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
            MarketplaceItemDetailUiEvent.OnDismissInfo ->
                _uiDataStateFlow.update { it.copy(infoMessage = null) }
            MarketplaceItemDetailUiEvent.OnDownloadClick -> {
                val s = _uiDataStateFlow.value
                if (!s.fileAccessUnlocked) return
                val url = s.item?.fileUrl
                if (!url.isNullOrBlank()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
            MarketplaceItemDetailUiEvent.OnGenerateOtp -> coroutineScope.launch {
                val s = _uiDataStateFlow.value
                val id = s.item?.numericId ?: return@launch
                if (!s.isOwner) return@launch
                _uiDataStateFlow.update { it.copy(isGeneratingOtp = true, errorMessage = null) }
                apiRepository.generateMaterialOtp(id).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> Unit
                        is NetworkResult.Success -> {
                            val d = result.data?.data
                            val deadline = computeOtpDeadlineWallClockMillis(d?.expiresAt, d?.serverTime)
                            _uiDataStateFlow.update {
                                it.copy(
                                    isGeneratingOtp = false,
                                    authorOtpCode = d?.otp,
                                    authorOtpDeadlineWallMs = deadline,
                                    infoMessage = "Share this OTP with your buyer (5 min)."
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            _uiDataStateFlow.update {
                                it.copy(isGeneratingOtp = false, errorMessage = result.message)
                            }
                        }
                        is NetworkResult.UnAuthenticated -> {
                            _uiDataStateFlow.update {
                                it.copy(
                                    isGeneratingOtp = false,
                                    errorMessage = "Please sign in to generate an OTP."
                                )
                            }
                        }
                    }
                }
            }
            is MarketplaceItemDetailUiEvent.OnBuyerOtpChange -> {
                val digits = event.value.filter { it.isDigit() }.take(6)
                _uiDataStateFlow.update { it.copy(buyerOtpInput = digits, errorMessage = null) }
            }
            MarketplaceItemDetailUiEvent.OnRedeemOtp -> coroutineScope.launch {
                val s = _uiDataStateFlow.value
                val id = s.item?.numericId ?: return@launch
                if (s.isOwner) return@launch
                val code = s.buyerOtpInput.trim()
                if (code.length != 6) {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Enter the 6-digit OTP.") }
                    return@launch
                }
                _uiDataStateFlow.update { it.copy(isRedeeming = true, errorMessage = null) }
                apiRepository.redeemMaterialOtp(id, code).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> Unit
                        is NetworkResult.Success -> {
                            _uiDataStateFlow.update {
                                it.copy(
                                    isRedeeming = false,
                                    hasRedeemedUnlock = true,
                                    buyerOtpInput = "",
                                    infoMessage = "Unlocked — you can open the file now."
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            _uiDataStateFlow.update {
                                it.copy(isRedeeming = false, errorMessage = result.message)
                            }
                        }
                        is NetworkResult.UnAuthenticated -> {
                            _uiDataStateFlow.update {
                                it.copy(
                                    isRedeeming = false,
                                    errorMessage = "Please sign in to redeem an OTP."
                                )
                            }
                        }
                    }
                }
            }
            is MarketplaceItemDetailUiEvent.OnRateMaterial -> coroutineScope.launch {
                val id = _uiDataStateFlow.value.item?.numericId ?: return@launch
                if (event.stars !in 1..5) return@launch
                _uiDataStateFlow.update { it.copy(isRatingLoading = true, errorMessage = null) }
                apiRepository.rateMaterial(id, event.stars).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> Unit
                        is NetworkResult.Success -> {
                            val dto = result.data?.data
                            val navId = _uiDataStateFlow.value.item?.id ?: itemId
                            val updated = dto?.toDetail(navId) ?: _uiDataStateFlow.value.item
                            _uiDataStateFlow.update {
                                it.copy(
                                    item = updated,
                                    isRatingLoading = false,
                                    infoMessage = "Rating saved."
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            _uiDataStateFlow.update {
                                it.copy(isRatingLoading = false, errorMessage = result.message)
                            }
                        }
                        is NetworkResult.UnAuthenticated -> {
                            _uiDataStateFlow.update {
                                it.copy(
                                    isRatingLoading = false,
                                    errorMessage = "Please sign in to rate materials."
                                )
                            }
                        }
                    }
                }
            }
            MarketplaceItemDetailUiEvent.OnDeleteClick ->
                _uiDataStateFlow.update { it.copy(showDeleteConfirm = true) }
            MarketplaceItemDetailUiEvent.OnDismissDeleteConfirm ->
                _uiDataStateFlow.update { it.copy(showDeleteConfirm = false) }
            MarketplaceItemDetailUiEvent.OnConfirmDelete -> coroutineScope.launch {
                val id = _uiDataStateFlow.value.item?.numericId ?: return@launch
                _uiDataStateFlow.update { it.copy(showDeleteConfirm = false, isDeleteLoading = true) }
                apiRepository.deleteMaterial(id).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> Unit
                        is NetworkResult.Success -> {
                            _uiDataStateFlow.update { it.copy(isDeleteLoading = false) }
                            navigate(NavigationAction.Pop())
                        }
                        is NetworkResult.Error -> {
                            _uiDataStateFlow.update {
                                it.copy(isDeleteLoading = false, errorMessage = result.message)
                            }
                        }
                        is NetworkResult.UnAuthenticated -> {
                            _uiDataStateFlow.update {
                                it.copy(
                                    isDeleteLoading = false,
                                    errorMessage = "Please sign in to delete materials."
                                )
                            }
                        }
                    }
                }
            }
            MarketplaceItemDetailUiEvent.OnCameraClick -> Unit
            MarketplaceItemDetailUiEvent.OnFileMoreClick -> Unit
        }
    }

    private fun StudyMaterialDto.toDetail(navId: String): MarketplaceItemDetail {
        val formats = buildList {
            add(MarketplaceItemFormat.NOTES)
            if (fileUrl.orEmpty().contains("pdf", true)) add(MarketplaceItemFormat.PDF)
        }
        val fname = fileUrl?.substringAfterLast("/")?.takeIf { it.isNotBlank() } ?: "document"
        return MarketplaceItemDetail(
            id = navId,
            numericId = id ?: 0,
            title = title.orEmpty().ifBlank { "Material" },
            authorName = user?.fullName ?: "Seller",
            authorSubtitle = "SELLER",
            rating = rating.toApiDouble(),
            ratingCount = 0,
            semesterLabel = category ?: "—",
            description = description.orEmpty(),
            formats = formats.ifEmpty { listOf(MarketplaceItemFormat.NOTES) },
            coverUrl = thumbnail,
            fileUrl = fileUrl,
            fileName = fname,
            fileMetaLine = listOfNotNull(category, createdAt?.take(10)).joinToString(" • "),
            ownerUserId = userId,
            authorEmail = user?.email,
            authorPhone = user?.phone,
            hasAccessFromApi = hasAccess == true
        )
    }

    private fun buildSample(itemId: String): MarketplaceItemDetail {
        val nId = itemId.toIntOrNull() ?: 0
        val title = when (itemId) {
            "t1" -> "Advanced Calculus"
            "f1" -> "Intro to Algorithms"
            else -> "Intro to Macroeconomics Notes"
        }
        return MarketplaceItemDetail(
            id = itemId,
            numericId = nId,
            title = title,
            authorName = "Sarah J.",
            authorSubtitle = "AUTHOR",
            rating = 4.8,
            ratingCount = 120,
            semesterLabel = "Spring '23",
            description = "Comprehensive notes covering chapters 1–5 of the standard curriculum.\n\nThese notes include detailed graphs for supply and demand and clear worked examples.",
            formats = listOf(MarketplaceItemFormat.NOTES, MarketplaceItemFormat.PDF),
            coverUrl = null,
            fileUrl = null,
            fileName = "Macro_Intro_Final.pdf",
            fileMetaLine = "2.4 MB • 14 PAGES • HIGH RES",
            ownerUserId = null,
            authorEmail = "seller@example.com",
            authorPhone = null,
            hasAccessFromApi = false
        )
    }
}
