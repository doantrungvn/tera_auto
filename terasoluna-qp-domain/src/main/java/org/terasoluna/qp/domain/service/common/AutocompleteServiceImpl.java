package org.terasoluna.qp.domain.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.terasoluna.qp.domain.model.Autocomplete;


public class AutocompleteServiceImpl implements
		AutocompleteService {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Autocomplete> list(AutocompleteInput autocompleteInput,
			String sourceType) {

		List<Autocomplete> list = new ArrayList<Autocomplete>();

		list = sqlSession.selectList(sourceType, autocompleteInput);
		return list;
	}

}
