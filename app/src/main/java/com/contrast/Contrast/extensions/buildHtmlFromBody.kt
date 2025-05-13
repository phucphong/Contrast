package com.contrast.Contrast.extensions

import com.contrast.Contrast.utils.Util


fun buildHtmlFromBody(
    rawHtml: String,
    screenWidthPx: Int,
    domain: String = ""
): String {
    val width = screenWidthPx - 20
    val header = """
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
        </head>
    """.trimIndent()

    val updatedBody = Util.updateImageSrc(rawHtml, domain)
        .replace("{{chieurong}}", "${width}px")
        .replace("{{chieucao}}", "${width / 2}px")

    return """
        <html>
        $header
        <body>
            $updatedBody
        </body>
        </html>
    """.trimIndent()
}
