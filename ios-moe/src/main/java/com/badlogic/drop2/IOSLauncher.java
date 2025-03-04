package com.badlogic.drop2;

import apple.uikit.c.UIKit;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.backends.iosmoe.IOSApplicationConfiguration;
import org.moe.natj.general.Pointer;
import com.badlogic.drop2.Main;

/** Launches the iOS (Multi-Os Engine) application. */
public class IOSLauncher extends IOSApplication.Delegate {
    protected IOSLauncher(Pointer peer) {
        super(peer);
    }

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration configuration = new IOSApplicationConfiguration();
        return new IOSApplication(new Main(), configuration);
    }

    public static void main(String[] argv) {
        UIKit.UIApplicationMain(0, null, null, IOSLauncher.class.getName());
    }
}