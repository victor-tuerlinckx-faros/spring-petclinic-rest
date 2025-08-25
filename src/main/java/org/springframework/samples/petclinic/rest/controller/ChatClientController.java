package org.springframework.samples.petclinic.rest.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.ToolingFacade;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class ChatClientController {
    private final ChatClient chatClient;
    private final ToolingFacade toolingFacade;
    private final String conversationId = UUID.randomUUID().toString();

    public ChatClientController(ChatClient.Builder builder, ToolingFacade toolingFacade, ChatMemory memory, VectorStore vectorStore) {
        this.chatClient = builder
            .defaultAdvisors(
                MessageChatMemoryAdvisor.builder(memory).conversationId(conversationId).build(),
                QuestionAnswerAdvisor.builder(vectorStore).build(),
                SimpleLoggerAdvisor.builder().build())
            .build();
        this.toolingFacade = toolingFacade;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String question) {
        var instruction = """
        You're an assistant in a Pet Clinic Application.

        When people tell you their name, greet them with their name and 'Welcome to Spring Pet Clinic, what can we do for you?'

        You should only answer questions about the following topics:
        - Pets
        - Owners
        - Veterinarians
        - Veterinarians fields of expertise, called specialties
        - Visits to the veterinarian
        - Different pet types

        You have access to tools.
        Only call a tool if the user explicitly asks for information that cannot be answered without the tool.
        If you can answer directly, do so without calling any tool.

        When asked about the job of a veterinarian, consult the vector store!

        When asked anything off-topic, respond with: 'I'm afraid I can't help you with that.'
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
