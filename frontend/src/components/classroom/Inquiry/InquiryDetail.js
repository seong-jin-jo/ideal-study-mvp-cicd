import React, { useContext, useEffect, useState } from "react";
import {
  deleteInquiry,
  readInquiry,
} from "../../../services/classroom/InquiryService.mjs";
import { useNavigate, useParams } from "react-router-dom";
import { AuthContext } from "../../../context/AuthContext";

// 문의 상세정보 및 수정삭제
const InquiryDetail = (classId) => {
  const { inquiryId } = useParams();
  const [inquiries, setInquiries] = useState([]);
  const navigate = useNavigate();
  const { userInfo } = useContext(AuthContext);

  useEffect(() => {
    const fetchInquiry = async () => {
      const data = await readInquiry(inquiryId);
      setInquiries(data);
    };
    fetchInquiry();
  }, [inquiryId]);

  const handleDelete = async () => {
    await deleteInquiry(inquiryId);
    navigate(`/inquiries`);
  };

  return inquiries ? (
    <div className="inquiries-detail">
      <h2>{inquiries.title}</h2>
      <p>{inquiries.content}</p>
      {userInfo.id === inquiries.authorId && (
        <>
          <button
            onClick={() =>
              navigate(`/class/${classId}/inquiries/edit/${inquiries.id}`)
            }
          >
            수정
          </button>
          <button onClick={handleDelete}>삭제</button>
        </>
      )}
      <button onClick={() => navigate(`/class/${classId}/inquiries`)}>
        목록으로
      </button>
    </div>
  ) : (
    <p>로딩 중...</p>
  );
};

export default InquiryDetail;
