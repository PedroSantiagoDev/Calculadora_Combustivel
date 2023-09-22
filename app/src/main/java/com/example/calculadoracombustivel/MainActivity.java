package com.example.calculadoracombustivel;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edLitros;
    private EditText edPrecoLitro;
    private TextView tvResultado;
    private Button btnCalcular;
    private Button btnLimparCampos; // Novo botão
    private Spinner primeiroMenu;
    private Spinner segundoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edLitros = findViewById(R.id.edLitros);
        edPrecoLitro = findViewById(R.id.edPrecoLitro);
        tvResultado = findViewById(R.id.tvResultado);
        primeiroMenu = findViewById(R.id.primeiroMenu);
        segundoMenu = findViewById(R.id.segundoMenu);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimparCampos = findViewById(R.id.btnLimparCampos); // Encontrar o botão "Limpar Campos"

        // Configurar um ouvinte de clique para o botão "Limpar Campos"
        btnLimparCampos.setOnClickListener(v -> limparCampos());

        ArrayAdapter<CharSequence> primeiroMenuAdapter = ArrayAdapter.createFromResource(this, R.array.tipos_combustivel, android.R.layout.simple_spinner_item);
        primeiroMenuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        primeiroMenu.setAdapter(primeiroMenuAdapter);

        ArrayAdapter<CharSequence> segundoMenuAdapter = ArrayAdapter.createFromResource(this, R.array.formas_pagamento, android.R.layout.simple_spinner_item);
        segundoMenuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        segundoMenu.setAdapter(segundoMenuAdapter);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCustoCombustivel();
            }
        });
    }

    private void calcularCustoCombustivel() {
        String formaPagamento = segundoMenu.getSelectedItem().toString();

        String litrosStr = edLitros.getText().toString().trim();
        String precoLitroStr = edPrecoLitro.getText().toString().trim();

        if (litrosStr.isEmpty() || precoLitroStr.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return; // Saia do método se os campos estiverem vazios
        }

        double litros = Double.parseDouble(litrosStr);
        double precoLitro = Double.parseDouble(precoLitroStr);

        double custoTotal = litros * precoLitro;

        if (formaPagamento.equals("Crédito")) {
            custoTotal *= 1.10; // Adicionar 10% para pagamento com cartão de crédito
        } else if (formaPagamento.equals("Espécie")) {
            custoTotal *= 0.95;
        } else {
            custoTotal *= 1.05; // adicionar de 5% para pagamento com PIX
        }

        tvResultado.setText(String.format("Custo total: R$ %.2f", custoTotal));
        tvResultado.setVisibility(View.VISIBLE); // Tornar o resultado visível
        btnLimparCampos.setVisibility(View.VISIBLE); // Tornar o botão "Limpar Campos" visível
    }

    private void limparCampos() {
        edLitros.setText("");
        edPrecoLitro.setText("");
        tvResultado.setText("");
        tvResultado.setVisibility(View.GONE); // Esconder o resultado
        btnLimparCampos.setVisibility(View.GONE); // Esconder o botão "Limpar Campos"
        primeiroMenu.setSelection(0); // Define a seleção do Spinner para a primeira opção
        segundoMenu.setSelection(0);  // Define a seleção do Spinner para a primeira opção
    }
}
