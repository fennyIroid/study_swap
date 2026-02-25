package com.studyswap.mobile.app.ux.sample;

/**
 * Sample template: UseCase that holds screen state and handles events.
 * Copy and rename (e.g. GetHomeUiStateUseCase), inject ApiRepository and call APIs in handleEvent.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J4\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000b0\u0013H\u0002J-\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000b0\u0013H\u0086\u0002J\u001c\u0010\u0017\u001a\u00020\u000b2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0013H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0019"}, d2 = {"Lcom/studyswap/mobile/app/ux/sample/GetBaseUiStateUseCase;", "", "()V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/studyswap/mobile/app/ux/sample/BaseUiDataState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "handleEvent", "", "context", "Landroid/content/Context;", "event", "Lcom/studyswap/mobile/app/ux/sample/BaseUiEvent;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "navigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "invoke", "Lcom/studyswap/mobile/app/ux/sample/BaseUiState;", "update", "block", "app_debug"})
public final class GetBaseUiStateUseCase {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.studyswap.mobile.app.ux.sample.BaseUiDataState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.studyswap.mobile.app.ux.sample.BaseUiDataState> state = null;
    
    @javax.inject.Inject()
    public GetBaseUiStateUseCase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.studyswap.mobile.app.ux.sample.BaseUiDataState> getState() {
        return null;
    }
    
    private final void update(kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.ux.sample.BaseUiDataState, com.studyswap.mobile.app.ux.sample.BaseUiDataState> block) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.studyswap.mobile.app.ux.sample.BaseUiState invoke(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope coroutineScope, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> navigate) {
        return null;
    }
    
    private final void handleEvent(android.content.Context context, com.studyswap.mobile.app.ux.sample.BaseUiEvent event, kotlinx.coroutines.CoroutineScope coroutineScope, kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> navigate) {
    }
}