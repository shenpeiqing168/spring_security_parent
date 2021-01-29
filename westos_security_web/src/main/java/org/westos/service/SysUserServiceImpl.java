package org.westos.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.westos.bean.SysRole;
import org.westos.bean.SysUser;
import org.westos.mapper.SysRoleMapper;
import org.westos.mapper.SysUserMapper;

import java.util.Date;
import java.util.List;

/**
 * @Author: ShenMouMou
 * @Company: 西部开源教育科技有限公司
 * @Description: 简简单单，只为教育。
 * @Date: 2021/1/6 13:54
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("username", username);
        SysUser sysUser = baseMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public SysUser findByPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return null;
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("mobile", phoneNumber);
        SysUser sysUser = baseMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser) {

        return baseMapper.selectPage(page, sysUser);
    }

    //点击修改用户时，所要查询的信息
    //1.根据用户id查出用户的旧信息
    //2.根据用户id查询用户原来所拥有的角色信息。
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public SysUser findById(Long id) {
        if (id == null) {
            return new SysUser();
        }
        //1. 根据用户id查出用户的旧信息
        SysUser sysUser = baseMapper.selectById(id);
        // 2. 根据用户id查询用户原来所拥有的角色信息。
        List<SysRole> list = sysRoleMapper.findByUserId(id);
        //3.把角色集合设置给用户
        sysUser.setRoleList(list);

        return sysUser;
    }


    //重写保存 或更新的方法
    /*
    * 更新用户表中数据
先将当前用户对应用户角色关系表中的数据删除
将新选择的角色保存到用户角色关系表中
    * */
    @Override
    @Transactional
    public boolean saveOrUpdate(SysUser sysUser) {
        //如果id为null说明是新增操作，那就给一个默认密码1234
        Long id = sysUser.getId();
        System.out.println("更新用户的id:" + id);
        if (sysUser.getId() == null) {
            sysUser.setPassword("$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm");
        }
        //设置更新时间
        sysUser.setUpdateDate(new Date());
        //更新用户表
        boolean flag = super.saveOrUpdate(sysUser);
        //如果用户表更新成功，那就更新用户和角色的中间表
        if (flag) {
            //现将该用户的原来有的角色，全部删除
            baseMapper.deleteUserRoleByUserId(sysUser.getId());
            //再添加更新的角色
            // 不为空则保存用户角色关系数据
            if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
                baseMapper.saveUserRole(sysUser.getId(), sysUser.getRoleIds());
            }
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        // 查询出用户信息
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setUpdateDate(new Date());
        // 是否可用设置为false表示删除
        sysUser.setEnabled(false);
        baseMapper.updateById(sysUser);
        return true;
    }
}
