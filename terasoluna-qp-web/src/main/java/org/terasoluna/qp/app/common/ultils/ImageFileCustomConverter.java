package org.terasoluna.qp.app.common.ultils;

import org.dozer.DozerConverter;

public class ImageFileCustomConverter extends DozerConverter<byte[], String> {

	public ImageFileCustomConverter() {
		super(byte[].class,String.class);
	}

	@Override
	public String convertTo(byte[] source, String destination) {
		
		if (source != null && source.length > 0) {
			return new sun.misc.BASE64Encoder().encode(source);
		}

		return null;
	}

	@Override
	public byte[] convertFrom(String source, byte[] destination) {
		// TODO Auto-generated method stub
		return destination;
	}
}
