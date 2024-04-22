package org.terasoluna.qp.app.common.ultils;

import java.io.IOException;

import org.dozer.CustomConverter;
import org.springframework.web.multipart.MultipartFile;

public class FileCustomConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		// TODO Auto-generated method stub
		if (sourceFieldValue == null) {
			return null;
		}
		if (sourceFieldValue instanceof MultipartFile) {
			try {
				return ((MultipartFile)sourceFieldValue).getBytes();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} 
		return null;
	}

}
