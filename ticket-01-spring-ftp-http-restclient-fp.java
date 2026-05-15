/*
 * Ticket 01 — HTTP / RestClient wrongly reported as FTP plaintext
 * Aqua check: spring-ftp-plaintext-communication-aquasec-java
 * Semgrep rule id: spring-ftp-plaintext-communication
 *
 * Verify (from repo root):
 *   semgrep --validate --config sast/aqua/java/spring-ftp-plaintext-communication.yml
 *   semgrep --test --config sast/aqua/java/spring-ftp-plaintext-communication.yml tests/sast/ticket-verification/ticket-01-spring-ftp-http-restclient-fp.java
 *
 * Expect: All tests pass (no FTP finding on HTTP; control ftp:// still ruleid).
 */

class Ticket01SpringFtpHttpFp {

    // --- False positive cases (must NOT match this rule) ---

    public void ok_literal_http_restclient() {
        // ok: spring-ftp-plaintext-communication
        RestClient restClient = RestClient.builder()
            .baseUrl("http://example.com/api")
            .build();
    }

    public void ok_concat_http_restclient_like_customer() {
        String host = "localhost";
        int port = 8080;
        // ok: spring-ftp-plaintext-communication
        String baseUrl = "http://" + host + ":" + port + "/path/v1";
        RestClient restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public void ok_http_webclient() {
        // ok: spring-ftp-plaintext-communication
        WebClient webClient = WebClient.builder()
            .baseUrl("http://api.example.com")
            .build();
    }

    public void ok_http_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // ok: spring-ftp-plaintext-communication
        String response = restTemplate.getForObject("http://api.example.com/data", String.class);
    }

    // --- Control: real plaintext FTP string should still match ---

    public void control_ftp_literal_must_flag() {
        DefaultFtpSessionFactory f = new DefaultFtpSessionFactory();
        // ruleid: spring-ftp-plaintext-communication
        f.setHost("ftp://control.example.com");
    }
}
