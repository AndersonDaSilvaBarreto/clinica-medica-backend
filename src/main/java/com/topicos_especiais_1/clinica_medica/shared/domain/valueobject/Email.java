package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoEmailInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public final class Email implements Serializable {

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    // Construtor padrão protegido exigido pelo Hibernate para hidratação via reflexão
    protected Email() {
    }

    private Email(@NonNull String value) {
        String normalizedEmail = value.trim().toLowerCase();
        Email.validate(normalizedEmail);
        this.value = normalizedEmail;
    }

    public static Email of(@NonNull String value) {
        return new Email(value);
    }

    private static void validate(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new FormatoEmailInvalidoException(FormatoEmailInvalidoException.INVALIDO);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
