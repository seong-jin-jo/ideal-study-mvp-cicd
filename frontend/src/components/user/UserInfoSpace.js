// 자기소개
import React from 'react';
import Button from '../Button';

const UserInfoSpace = (user, isAuthenticated) => {

    const handleUpdateProfilePage = () =>{
        console.log("얼로보내지")
    }

    return (
      <div>
        <h3>김대민</h3> 
        <div className="profile-header">
          <h2>{user.name}</h2>
        </div>
        <div className="profile-details">
          <p><strong>이메일:</strong> {user.email}</p>
          <p><strong>연락처:</strong> {user.phone}</p>
          <p><strong>레벨:</strong> {user.level}</p>
        </div>
        {/* { isAuthenticated && <Button>수정</Button> } */}
      </div>
    );
};

export default UserInfoSpace;