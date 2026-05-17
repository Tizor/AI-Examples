//package app
//
//import chat.giga.client.GigaChatClient
//import chat.giga.client.auth.AuthClient
//import chat.giga.client.auth.AuthClientBuilder
//import chat.giga.model.Scope
//import org.springframework.ai.chat.client.ChatClient
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//open class Config {
//
////    @Bean
////    open fun chatClient() = GigaChatClient.builder()
////        .verifySslCerts(false)
////        .authClient(
////            AuthClient.builder()
////            .withOAuth(
////                AuthClientBuilder.OAuthBuilder.builder()
////                .scope(Scope.GIGACHAT_API_PERS)
//////                    .clientId("deefd4cf-42ff-4ebe-be9b-cc4cd47a293f")
//////                    .clientSecret("019e2fc3-cbf5-77a4-82c4-4323c8c6c0a8")
////                .authKey("MDE5ZTJmYzMtY2JmNS03N2E0LTgyYzQtNDMyM2M4YzZjMGE4OmRlZWZkNGNmLTQyZmYtNGViZS1iZTliLWNjNGNkNDdhMjkzZg==")
////                .build())
////            .build())
////        .maxRetriesOnAuthError(3)
////        .build();
//
//
//}