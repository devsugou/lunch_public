package com.station.lunch.exception;

/**
 * 画像の処理中に問題が発生した場合にスローされる例外です。
 * このクラスはチェック例外であり、この例外をスローするメソッドは
 * `throws` キーワードを使用して例外を宣言する必要があります。
 * 呼び出し元はこの例外をキャッチして適切に処理する必要があります。
 */
public class ImageProcessingException extends Exception {

    // Javaのシリアル化メカニズムで必要となる一意の識別子
    private static final long serialVersionUID = 1L;

    /**
     * 指定された詳細メッセージで新しい例外を構築します。
     *
     * @param message 詳細メッセージ（あとで Throwable.getMessage() メソッドで取得できます）
     */
    public ImageProcessingException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージと原因で新しい例外を構築します。
     *
     * @param message 詳細メッセージ（あとで Throwable.getMessage() メソッドで取得できます）
     * @param cause 原因（あとで Throwable.getCause() メソッドで取得できます。null値も許可されています）
     */
    public ImageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
