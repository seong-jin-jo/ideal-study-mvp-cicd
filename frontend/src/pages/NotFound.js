import React from 'react';
import { Link } from 'react-router-dom';

const NotFound = () => {
  
  //디버깅
  console.log("NotFound 컴포넌트가 렌더링되었습니다.");

  return (
    <div>
      <h1>404</h1>
      <p>페이지를 찾을 수 없습니다.</p>
      <Link to="/">홈으로 돌아가기</Link>
    </div>
  );
};

export default NotFound;