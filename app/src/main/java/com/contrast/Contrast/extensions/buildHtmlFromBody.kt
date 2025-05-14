package com.contrast.Contrast.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.contrast.Contrast.utils.Util
import org.jsoup.Jsoup
import org.jsoup.safety.Safelist

fun cleanHtml(inputHtml: String): String {
    // Làm sạch HTML và giữ lại các thẻ cơ bản như <p>, <ul>, <li>, <strong>, <h1>...
    return Jsoup.clean(inputHtml, Safelist.relaxed())
}

fun buildHtmlFromBody(
    rawHtml: String,
    screenWidthPx: Int,
    domain: String = ""
): String {
    val width = screenWidthPx - 20

    val header = """
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
            <style type="text/css">
                @font-face {
                    font-family: MyFont;
                    src: url("file:///android_asset/font/ezmax.ttf");
                }
                body {
                    font-family: MyFont;
                    font-size: medium;
                    text-align: justify;
                    color: #333;
                    padding: 16px;
                    line-height: 1.6;
                }
                img {
                    max-width: 100%;
                    height: auto;
                }
            </style>
        </head>
    """.trimIndent()

    val cleanedBody = Util.updateImageSrc(rawHtml, domain)
        .replace("{{chieurong}}", "${width}px")
        .replace("{{chieucao}}", "${width / 2}px")
        .replace(Regex("[\\u0000-\\u001F]"), "") // loại bỏ ký tự không hợp lệ

    return """
        <html>
        $header
        <body>
            $cleanedBody
        </body>
        </html>
    """.trimIndent()
}
