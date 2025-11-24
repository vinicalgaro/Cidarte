package com.vinicalgaro.cidarte.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.vinicalgaro.cidarte.R

@Composable
fun DefaultErrorComponent(
    onRetry: (() -> Unit)? = null,
    text: String = stringResource(R.string.error_ao_carregar_conteudo)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_img),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(16.dp))
            IconButton(
                onClick = onRetry,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Lucide.RefreshCw,
                    contentDescription = stringResource(R.string.tentar_novamente),
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}