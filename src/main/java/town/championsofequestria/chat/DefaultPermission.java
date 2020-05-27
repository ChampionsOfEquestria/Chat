package town.championsofequestria.chat;

import java.util.UUID;

import org.sweetiebelle.lib.permission.PermissionManager;

public class DefaultPermission implements PermissionManager {

    public final static String DEFAULT_PREFIX = "";
    public final static String DEFAULT_SUFFIX = "";
    public final static String DEFAULT_GROUP = "";

    @Override
    public String getCompletePlayerPrefix(UUID arg0) {
        return DEFAULT_PREFIX;
    }

    @Override
    public String getCompletePlayerSuffix(UUID arg0) {
        return DEFAULT_SUFFIX;
    }

    @Override
    public String getGroupPrefix(UUID arg0) {
        return DEFAULT_PREFIX;
    }

    @Override
    public String getGroupSuffix(UUID arg0) {
        return DEFAULT_SUFFIX;
    }

    @Override
    public String getPlayerPrefix(UUID arg0) {
        return DEFAULT_PREFIX;
    }

    @Override
    public String getPlayerSuffix(UUID arg0) {
        return DEFAULT_SUFFIX;
    }

    @Override
    public String getPrimaryGroup(UUID arg0) {
        return DEFAULT_GROUP;
    }
}
