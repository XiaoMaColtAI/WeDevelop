package org.example.wedevelop.langgraph4j.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AppNamingService {

    @SystemMessage(fromResource = "prompt/app-naming-system-prompt.txt")
    String generateAppName(@UserMessage String initPrompt);
}
