import React from "react";
import "./MaterialListPage.css";
import Button from "../../../../components/Button";
const MaterialListPage = () => {
  return (
    <div>
      <div className="materialListPageHeader">
        <h2>자료 목록</h2>
        <Button>자료 추가</Button>
      </div>
      <div className="materialListPageContent">
        <div className="materialListPageItem">
          <h3>제목</h3>
          <p>설명</p>
        </div>
        <div className="materialListPageFooter">
          <button>자료 다운로드</button>
          <button>자료 삭제</button>
          <button>자료 수정</button>
          <button>자료 공유</button>
        </div>
      </div>
    </div>
  );
};

export default MaterialListPage;
