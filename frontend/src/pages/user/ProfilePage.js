import React, { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { useParams } from 'react-router-dom';
import { readUser } from '../../services/UserService.mjs';
import UserInfoSpace from '../../components/user/UserInfoSpace';
import Photos from '../../components/user/Photos';
import Schedular from '../../components/user/Schedular';
import GuestBook from '../../components/user/GuestBook';

import './ProfilePage.css';
import Bio from '../../components/user/Bio';

const ProfilePage = () => {
    const { id } = useParams();
    const [user, setUser] = useState(null); // 유저 정보
    const { userInfo } = useContext(AuthContext);

    useEffect( ()=>{
        const fetchUserProfile = async () => {
            try {
              const data = await readUser(id);
              setUser(data);
            } catch (error) {
              console.error('Error fetching user detail:', error);
            }
          };
          fetchUserProfile();
    },[id])

    if (!user) return <p>Loading...</p>;

    // 디버깅
    console.log("userInfo.id", userInfo.id);
    console.log("id", id);

    return (
        <div className="profile-container">
            <div className="section"><UserInfoSpace user={user} isAuthenticated={userInfo.id === id} /></div>
            <div className="section"><Bio isAuthenticated={userInfo.id === id}/></div>
            <div className="section"><Photos isAuthenticated={userInfo.id === id} /></div>
            <div className="section"><Schedular isAuthenticated={userInfo.id === id} /></div>
            <div className="section"><GuestBook isAuthenticated={userInfo.id === id} /></div>
        </div>
    );    
};

export default ProfilePage;