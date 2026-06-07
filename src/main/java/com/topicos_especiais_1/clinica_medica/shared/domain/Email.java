package com.topicos_especiais_1.clinica_medica.shared.domain;

import com.topicos_especiais_1.clinica_medica.shared.exception.FormatoEmailInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Email {
    @Column(name = "email",nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String value;

    private Email(@NonNull String value) {
        var normalizedEmail = value.trim().toLowerCase();
        Email.validate(normalizedEmail);
        this.value = normalizedEmail;
    }

    public static Email of(@NonNull String value) {
        return new Email(value);
    }

    private static void validate(@NonNull String email) {
        if (email.isBlank()) {
            throw new FormatoEmailInvalidoException(FormatoEmailInvalidoException.VAZIO);
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new FormatoEmailInvalidoException(FormatoEmailInvalidoException.INVALIDO);
        }
    }

    @Override
    public String toString() {
        return getValue();
    }
}
