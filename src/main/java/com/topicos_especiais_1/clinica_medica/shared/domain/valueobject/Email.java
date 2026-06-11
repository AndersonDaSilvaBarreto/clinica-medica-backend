package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoEmailInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Email implements Serializable {
    @Column(name = "email",nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String value;

    private Email(@NonNull String value) {
        var normalizedEmail = value.trim().toLowerCase();
        Email.validate(normalizedEmail);
        this.value = normalizedEmail;
    }

    public static Email of( String value) {
        return new Email(Objects.requireNonNull(value));
    }

    private static void validate(String email) {
        Objects.requireNonNull(email);
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new FormatoEmailInvalidoException(FormatoEmailInvalidoException.INVALIDO);
        }
    }

    @Override
    public String toString() {
        return getValue();
    }
}
