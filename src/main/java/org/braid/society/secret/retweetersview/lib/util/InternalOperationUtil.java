package org.braid.society.secret.retweetersview.lib.util;

import lombok.extern.slf4j.Slf4j;
import org.braid.society.secret.retweetersview.lib.util.enums.ExitCodeEnum;

@Slf4j
public class InternalOperationUtil {

    /**
     * No instances
     */
    private InternalOperationUtil() {}

    /**
     * exit this software, backing up necessary files if needed.
     * @param exitCode value to set to VM.
     */
    public static void exit(int exitCode) {
        log.info("Attempt to exit this software.");
        // code here for await concurrent thread or backing up necessary files if needed.
        System.exit(exitCode);
    }

    public static void exit(ExitCodeEnum enumExitCode) {
        exit(enumExitCode.getCode());
    }

}
