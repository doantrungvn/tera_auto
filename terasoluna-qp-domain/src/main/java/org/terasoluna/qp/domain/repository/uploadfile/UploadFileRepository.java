package org.terasoluna.qp.domain.repository.uploadfile;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.UploadFile;

@Repository
public interface UploadFileRepository extends Serializable {
	UploadFile findOne(Long uploadFileId);
	
	int register(UploadFile uploadFile);

	int modify(UploadFile uploadFile);
	
	int delete(Long uploadFileId);
}
