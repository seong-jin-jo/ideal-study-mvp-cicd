import React, { useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import Button from '../../components/Button';
import { useNavigate } from 'react-router-dom';


const HomePage = () => {
  const { logout } = useContext(AuthContext);
  const { isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate(); // useNavigate 훅을 사용하여 네비게이션

  const handleLoginNavigate = () => {
    navigate('/login'); // 로그인 페이지로 이동
  };

  const handleLogoutNavigate = () => {
    logout();
    navigate('/'); // 로그아웃 페이지로 이동
  };

  const handleSignUpNavigate = () => {
    navigate('/signup'); // 로그아웃 페이지로 이동
  };

  return (
    <div>
      <h1>메인 페이지</h1>
      {isAuthenticated ? (
        <div>
          <p>로그인 상태입니다.</p>
          <Button onClick={handleLogoutNavigate}>로그아웃</Button> {/* Button 컴포넌트 사용 */}
        </div>
      ) : (
        <div> {/* 여러 요소를 감싸는 부모 div 추가 */}
          <p>비로그인 상태입니다.</p>
          <Button onClick={handleSignUpNavigate}>회원가입</Button> {/* Button 컴포넌트 사용 */}
          <Button onClick={handleLoginNavigate}>로그인</Button> {/* Button 컴포넌트 사용 */}
        </div>
      )}
    </div>
  );
};

export default HomePage;