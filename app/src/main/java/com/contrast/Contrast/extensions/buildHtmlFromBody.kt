package com.contrast.Contrast.extensions


import com.contrast.Contrast.utils.Util

import androidx.core.text.HtmlCompat

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

    // ✅ Decode HTML escape (rất quan trọng)
    val unescaped = HtmlCompat.fromHtml(rawHtml, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    val cleanedBody = Util.updateImageSrc(unescaped, domain)
        .replace("{{chieurong}}", "${width}px")
        .replace("{{chieucao}}", "${width / 2}px")
        .replace(Regex("[\\u0000-\\u001F]"), "")

    return """
        <html>
        $header
        <body>
            $cleanedBody
        </body>
        </html>
    """.trimIndent()
}
