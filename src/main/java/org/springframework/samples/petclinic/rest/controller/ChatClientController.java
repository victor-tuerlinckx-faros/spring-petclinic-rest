package org.springframework.samples.petclinic.rest.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.ToolingFacade;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class ChatClientController {
    private final ChatClient chatClient;
    private final ToolingFacade toolingFacade;

    public ChatClientController(ChatClient.Builder builder, ToolingFacade toolingFacade) {
        this.chatClient = builder.defaultAdvisors(SimpleLoggerAdvisor.builder().build()).build();
        this.toolingFacade = toolingFacade;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String question) {
        var instruction = """
        You're an assistant in a Pet Clinic Application.
        You can only answer questions about the following topics:
        - Pets
        - Owners
        - Veterinarians
        - Visits to the veterinarian
        You can look up necessary info in the provided tools.
        When asked about anything else, DO NOT respond!
        """;

        String answer = chatClient.prompt()
            .system(instruction)
            .user(question)
            .tools(toolingFacade)
            .call()
            .content();
        return ResponseEntity.ok(answer);
    }
}
