package io.helidon.atp.lab.service.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import io.helidon.atp.lab.entity.Emp;
import io.helidon.atp.lab.entity.EmpExample;
import io.helidon.atp.lab.mapper.EmpMapper;
import io.helidon.atp.lab.service.EmpService;

@RequestScoped
public class EmpServiceImpl implements EmpService {

	@Inject
	EmpMapper empMapper;

	@Override
	public long countByExample(EmpExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(EmpExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Emp record) {

		return empMapper.insert(record);
	}

	@Override
	public int insertSelective(Emp record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Emp> selectByExample(EmpExample example) {

		return empMapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(Emp record, EmpExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(Emp record, EmpExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

}
