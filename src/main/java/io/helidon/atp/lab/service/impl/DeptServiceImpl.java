package io.helidon.atp.lab.service.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import io.helidon.atp.lab.entity.Dept;
import io.helidon.atp.lab.entity.DeptExample;
import io.helidon.atp.lab.mapper.DeptMapper;
import io.helidon.atp.lab.service.DeptService;

@RequestScoped
public class DeptServiceImpl implements DeptService {

	@Inject
	DeptMapper deptMapper;

	@Override
	public long countByExample(DeptExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(DeptExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Dept record) {

		return deptMapper.insert(record);
	}

	@Override
	public int insertSelective(Dept record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Dept> selectByExample(DeptExample example) {

		return deptMapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(Dept record, DeptExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(Dept record, DeptExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

}
