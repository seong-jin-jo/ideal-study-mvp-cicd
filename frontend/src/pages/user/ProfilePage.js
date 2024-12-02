import React, { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { useParams } from 'react-router-dom';
import { readUser } from '../../services/user/UserService.mjs';
import { readBio } from '../../services/MyPageService.mjs';

import UserInfoSpace from '../../components/user/UserInfoSpace';
import Photos from '../../components/user/Photos';
import Schedular from '../../components/user/Schedular';
import GuestBook from '../../components/user/GuestBook';

import './ProfilePage.css';
import Bio from '../../components/user/Bio';

const ProfilePage = () => {
    const { id } = useParams();
    const { userInfo } = useContext(AuthContext);
    const [user, setUser] = useState(null); // 유저 정보
    const [bio, setBio] = useState(null); // 유저 자기소개
    
    useEffect( ()=>{
        const fetchUserProfile = async () => {
            const data = await readUser(id);
            setUser(data);
        };

        const fetchReadBio = async() =>{
            const data = await readBio(id);
            setBio(data);
        }

        fetchUserProfile();
        fetchReadBio();
    },[id])

    if (!user) return <p>Loading...</p>;

    // 디버깅
    console.log("userInfo.id", userInfo.id);
    console.log("id", id);
    console.log("[ProfilePage] Bio컴포넌트로 넘겨줄 bio",bio);

    return (
        <div className="profile-container">
            <div className="section"><UserInfoSpace user={user} isAuthenticated={userInfo.id === id} /></div>
            <div className="section"><Bio user={user} fetchedBio={bio} isAuthenticated={userInfo.id === id}/></div>
            <div className="section unOpend"><Photos isAuthenticated={userInfo.id === id} /></div>
            <div className="section unOpend"><Schedular isAuthenticated={userInfo.id === id} /></div>
            <div className="section unOpend"><GuestBook isAuthenticated={userInfo.id === id} /></div>
        </div>
    );    
};

export default ProfilePage;