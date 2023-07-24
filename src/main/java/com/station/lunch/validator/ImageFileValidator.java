package com.station.lunch.validator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

/**
 * 画像ファイルのバリエーションクラス
 */
public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    // ファイルの最大サイズ（バイト単位で、ここでは5MBを示しています）
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // 許可されるファイル形式
    private static final String[] ALLOWED_CONTENT_TYPES = { "image/jpeg", "image/png" };
    
    @Override
    public void initialize(ValidImage constraintAnnotation) {
        // Initialization code, if needed
    }
    
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // ファイルが空の場合は有効とします
        if (file.isEmpty()) {
            return true;
        }
        // ファイルの最大サイズチェック
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }

        String contentType = file.getContentType();
        for (String allowedContentType : ALLOWED_CONTENT_TYPES) {
            if (allowedContentType.equals(contentType)) {
                return true;
            }
        }

        // Check if the file's contents are actually an image
        try (InputStream is = file.getInputStream()) {
            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                return false;
            }
        } catch (IOException e) {
            // Handle exception
        }

        return true;
    }
}

