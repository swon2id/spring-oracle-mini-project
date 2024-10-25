document.addEventListener('DOMContentLoaded', () => {
  const searchInput = document.querySelector('.search-box input');
  const searchButton = document.querySelector('.search-box button');

  // 검색 버튼 클릭 이벤트
  searchButton.addEventListener('click', () => {
    const query = searchInput.value.trim();
    if (query === "") {
    } else {
      // URL에 쿼리스트링 추가하여 이동
      window.location.href = `/search?term=${encodeURIComponent(query)}`;
    }
  });

  // 검색 입력 필드에서 Enter 키 입력 이벤트
  searchInput.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
      const query = searchInput.value.trim();
      if (query === "") {
        event.preventDefault(); // 입력 값이 없으면 동작 막기
      } else {
        // URL에 쿼리스트링 추가하여 이동
        window.location.href = `/search?term=${encodeURIComponent(query)}`;
      }
    }
  });
});
