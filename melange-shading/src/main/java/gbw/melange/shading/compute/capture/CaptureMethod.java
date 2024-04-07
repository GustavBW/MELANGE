package gbw.melange.shading.compute.capture;

import java.util.function.Supplier;

public enum CaptureMethod {
    FRAMEBUFFER(FramebufferCapture::new),
    BUFFER_OBJECT(() -> null),
    PIXEL_PACK_BUFFER(() -> null),
    SHARED_MEMORY(() -> null);

    private final Supplier<ICaptureMethod<?>> supplier;

    CaptureMethod(Supplier<ICaptureMethod<?>> supplier) {
        this.supplier = supplier;
    }

    public ICaptureMethod<?> create() {
        return supplier.get();
    }
}
