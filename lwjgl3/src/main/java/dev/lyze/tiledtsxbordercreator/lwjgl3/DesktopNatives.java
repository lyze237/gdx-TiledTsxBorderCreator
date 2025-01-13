package dev.lyze.tiledtsxbordercreator.lwjgl3;

import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.nfd.NativeFileDialog;

public class DesktopNatives implements IDesktopNatives {
    @Override
    public String openFilePicker() {
        var path = MemoryUtil.memAllocPointer(1);

        var result = NativeFileDialog.NFD_OpenDialog((CharSequence) null, null, path);

        String selectedFile = null;
        if (result == NativeFileDialog.NFD_OKAY) {
            selectedFile = path.getStringUTF8(0);
        }

        MemoryUtil.memFree(path);
        return selectedFile;
    }

    @Override
    public String openFolderPicker() {
        var path = MemoryUtil.memAllocPointer(1);

        var result = NativeFileDialog.NFD_PickFolder((CharSequence) null, path);

        String selectedFile = null;
        if (result == NativeFileDialog.NFD_OKAY) {
            selectedFile = path.getStringUTF8(0);
        }

        MemoryUtil.memFree(path);
        return selectedFile;
    }
}
