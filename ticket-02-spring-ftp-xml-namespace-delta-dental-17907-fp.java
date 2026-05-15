/*
 * Ticket 02 — Delta Dental of Michigan (Customer ID 17907)
 * XML QName / @XmlRootElement namespace strings wrongly reported as FTP plaintext
 * Aqua check: spring-ftp-plaintext-communication-aquasec-java
 * Semgrep rule id: spring-ftp-plaintext-communication
 *
 * Verify (from repo root):
 *   semgrep --validate --config sast/aqua/java/spring-ftp-plaintext-communication.yml
 *   semgrep --test --config sast/aqua/java/spring-ftp-plaintext-communication.yml tests/sast/ticket-verification/ticket-02-spring-ftp-xml-namespace-delta-dental-17907-fp.java
 *
 * Expect: All tests pass (XML/JAXB ok; control ftp:// still ruleid).
 */

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

@XmlRootElement(name = "NameType", namespace = "http://www.example.com/v2")
class Ticket02DeltaDentalXmlNamespace {
    public void test() {
        // ok: spring-ftp-plaintext-communication
        QName q = new QName("http://www.w3.org/2001/XMLSchema", "string");
        System.out.println(q);
    }

    public void ok_set_xml_type_schema_qname() {
        // ok: spring-ftp-plaintext-communication
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
    }

    public void ok_custom_namespace_qname() {
        // ok: spring-ftp-plaintext-communication
        QName q2 = new QName("http://www.example.com/v2", "NameType");
        System.out.println(q2);
    }

    // --- Control: actual FTP URL must still match ---

    public void control_ftp_must_flag() {
        // ruleid: spring-ftp-plaintext-communication
        String ftpUrl = "ftp://files.control.example.invalid/uploads";
        sink(ftpUrl);
    }

    void sink(String s) {}
}

@XmlRootElement(name = "Ticket02AnnotationOnly", namespace = "http://www.example.com/v2")
class Ticket02AnnotationNamespaceOnly {
}
