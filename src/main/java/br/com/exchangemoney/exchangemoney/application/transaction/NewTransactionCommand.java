package br.com.exchangemoney.exchangemoney.application.transaction;

import com.sun.istack.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class NewTransactionCommand {

    String taxId;

    BigDecimal amount;

    @NotNull
    String from;

    @NonNull
    String to;

    public enum Rate {
        BRL, USD, EUR, JPY;

        public static String getBaseSymbols() {
            return stream(Rate.values()).map(Enum::name)
                    .collect(Collectors.toList())
                    .toString().replaceAll("\\[|\\]", "").trim();
        }
    }

}
