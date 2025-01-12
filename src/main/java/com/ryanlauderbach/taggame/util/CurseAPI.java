package com.ryanlauderbach.taggame.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ryanlauderbach.taggame.TagGame;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class CurseAPI {
    
    // https://github.com/google/gson/blob/master/UserGuide.md#collections-examples
    public static void checkForUpdates() throws IOException {
        TagGame.getPlugin().getLogger().info("Checking for updates...");
        URL url = new URL("https://api.curseforge.com/servermods/files?projectIds=405163");
        InputStreamReader reader = new InputStreamReader(url.openStream());
        Type collectionType = new TypeToken<List<File>>(){}.getType();
        List<File> filesCollection = new Gson().fromJson(reader, collectionType);
        reader.close();

        if (filesCollection.isEmpty()) {
            TagGame.getPlugin().getLogger().warning("Unable to check for updates");
            return;
        }
        
        if (!filesCollection.get(filesCollection.size() - 1).name.contains(TagGame.version)) {
            TagGame.getPlugin().getLogger().warning("New version available: " + filesCollection.get(filesCollection.size() - 1).name);
            TagGame.getPlugin().getLogger().warning("Currently on version: Tag v" + TagGame.version);
        } else {
            TagGame.getPlugin().getLogger().info("No updates available");
        }
    }
}

class File {
    String name;
}
