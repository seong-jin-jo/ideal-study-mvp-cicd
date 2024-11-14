import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Button from '../components/Button';

const Header = () => {
  const { logout, isAuthenticated, userInfo } = useContext(AuthContext);
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
    <header style={{ padding: '10px', backgroundColor: '#f1f1f1' }}>
      <h1>사이트 이름</h1>
      <nav style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0'}}>
        <div>
          <Link to="/">홈</Link> | <Link to="/about">소개</Link> | <Link to="/teachers">강사목록</Link> | <Link to="/students">학생목록</Link>
          {isAuthenticated && <> | <Link to={`/myPage/${userInfo.id}`}>마이페이지</Link></>}
        </div>
        <div>
        {isAuthenticated ? (
          <div>
            {userInfo.role === 'teacher' && <> <Link to={`/officialPage/${userInfo.id}`}>강사공식페이지</Link></>}
             &nbsp; [{userInfo.role}] {userInfo.name} (Lv.{userInfo.level}) &nbsp;
            <Button onClick={handleLogoutNavigate}>로그아웃</Button> {/* Button 컴포넌트 사용 */}
          </div>
        ) : (
          <div>
            <Button onClick={handleSignUpNavigate}>회원가입</Button> {/* Button 컴포넌트 사용 */}
            <Button onClick={handleLoginNavigate}>로그인</Button> {/* Button 컴포넌트 사용 */}
          </div>
        )}
        </div>
      </nav>
    </header>
  );
};

export default Header;
