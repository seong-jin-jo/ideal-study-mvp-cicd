import React, { useContext, useEffect } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { makeDummyUser } from '../../services/UserService.mjs';
import Button from '../../components/Button';

const HomePage = () => {
  const { isAuthenticated } = useContext(AuthContext);

  const makeDummy = async() => {
      await makeDummyUser(); 
  }

  return (
    <div>
      <main style={{ padding: '10px' }}>
        <Button onClick={makeDummy}> 더미유저 생성하기 </Button>
        <h1>메인 페이지</h1>
        {isAuthenticated ? (
          <div>
            <p>로그인 상태입니다.</p>
          </div>
        ) : (
          <div> {/* 여러 요소를 감싸는 부모 div 추가 */}
            <p>비로그인 상태입니다.</p>
          </div>
        )}
      </main>
    </div>
  );
};

export default HomePage;