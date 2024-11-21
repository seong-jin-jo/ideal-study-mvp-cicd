import React, { useState, useEffect, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { readUser, updateUser } from '../../services/UserService.mjs';
import { useNavigate } from 'react-router-dom';

// 계정 정보 변경에 관한 페이지

const AccountSettings = () => {
    /*
        isFirst 가 true 이면 이 페이지로 이동됨
    */
    const location = useLocation();
    const navigate = useNavigate();
    const { userInfo } = useContext(AuthContext);
    const [formData,setFormData] = useState({
        name: '',
        password: '',
        phone: '',
        email: '',
        school: '',
        gender: '',
        grade: '',
    });
    

    // 개인정보 조회
    const pathUserId = location.pathname.split('/').pop();

    useEffect(() => {
        // 로그인한 유저와 보려는 정보가 같을때만 개인정보보여줌
        if(userInfo.id !== pathUserId){
            alert("권한 없어요");
            navigate("/forbidden"); // 만들어야함
        }
        const fetchAccountSettings = async () => {
            const data = await readUser(userInfo.id);
            console.log("개인정보 데이터",data);
            setFormData(data);
        }
        fetchAccountSettings();
    },[userInfo.id, formData]) // (리팩토링) 입력시 계속 리랜더링할듯 

    // 개인정보 수정
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
      };
    
    // 개인정보 수정요청
    const handleSubmit = async(e) => {
        e.preventDefault();

        // 수정내용 제출
        await updateUser(formData);

        // 제출완료시 계정조회 페이지로 이동
        navigate(`/accountSettings/${userInfo.id}`)
        console.log("이동할 유저아이디",userInfo.id);
    }

    return (
      <div>
      <h2>개인정보 수정</h2>
        <form onSubmit={handleSubmit}>
          <label>
            이름:
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
            //   required
            />
          </label>
          <br />
  
          <label>
            비밀번호:
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
            //   required
            />
          </label>
          <br />
  
          <label>
            휴대폰 번호:
            <input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
            //   required
            />
          </label>
          <br />
  
          <label>
            이메일 주소:
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
            //   required
            />
          </label>
          <br />
  
          <label>
            학교:
            <input
              type="text"
              name="school"
              value={formData.school}
              onChange={handleChange}
            />
          </label>
          <br />
  
          <label>
            성별:
            <select name="gender" value={formData.gender} onChange={handleChange}>
              <option value="">선택</option>
              <option value="male">남성</option>
              <option value="female">여성</option>
              <option value="other">기타</option>
            </select>
          </label>
          <br />
  
          <label>
            학년:
            <select name="grade" value={formData.grade} onChange={handleChange}>
              <option value="">선택</option>
              <option value="1">1학년</option>
              <option value="2">2학년</option>
              <option value="3">3학년</option>
              <option value="4">4학년</option>
            </select>
          </label>
          <br />
  
          <button type="submit">저장</button>
        </form>
      </div>
    );
};

export default AccountSettings;