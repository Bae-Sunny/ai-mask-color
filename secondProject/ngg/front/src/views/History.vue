<template>
  <MainLayout>
    <div class="all-padding"></div>
    <div class="my-container" style="padding-top: 30px; padding: 10px; font-size: 25px;">
      <div>
        <h1 style="padding-top: 30px;">나의 기록</h1>
        <hr />
      </div>
      <div style="width: 100%; height: 100%; padding-bottom: 30px; padding-top: 30px;">
        <table style="width: 100%; height: 100%">
          <tr>
            <td class="object" style="border-right: solid 2px rgba(0, 0, 0, 0.3); width: 25%; height: 30%; padding-bottom: 30px;">
              <div style="width: 100%; height: 100%; margin: auto">
                <div>
                  <div class="mypage-box" style="width: 50%; height: 50%; margin: auto">
                   <img
                      v-show="detail.mimgn"
                      :src="`/img/upimg/${detail.mimgn}`"
                      class="mypage-profile"
                      style="width: 100%; height: 100%;"
                    />
                    <img
                      v-show="!detail.mimgn"
                      src="/img/MakeUp_image/upload3.png"
                      class="mypage-profile"
                      style="width: 100%; height: 100%;"
                    />
                  </div>
                  <br>
                  <h1 style="text-align: center">{{ detail.mnick }}</h1>
                </div>
                <hr style="width: 50%; margin: auto; padding-bottom: 15px; margin-top: 15px; margin-bottom: 5px;">
                <div>
                  <a href="/MyPage" class="custom-link">개인정보</a><br />
                  <router-link :to="`/History/${detail.mnum}`" class="custom-link">나의 기록</router-link><br />
                  <router-link :to="`/MyProfile/${detail.mnum}`" class="custom-link">프로필 변경</router-link><br />
                  <hr style="width: 50%; margin: auto; padding-bottom: 15px; margin-top: 15px;" />
                  <a href="" class="custom-link">로그아웃</a><br />
                  <a @click.stop="delAccount(detail.mnum)" class="custom-link" style="cursor: pointer;">회원탈퇴</a><br />  
                </div>
              </div>
            </td>
            <td rowspan="2" style="text-align: center; width: 75%; margin: auto;">
              <div class="row row-cols-1 row-cols-md-3 g-4" style="margin: auto">
                <div v-for="(card, index) in paginatedCards" :key="index" class="col history">
                  <div class="card" style="border-radius: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);">
                    <div class="card-body">
                      <div class="flip" @click="flipCard(index)">
                        <div class="flip-inner" :class="{ flipped: card.isFlipped }">
                          <div class="flip-front">
                            <ul class="history-ul">
                              <b>{{ card.date }}</b>
                            </ul>
                            <img :src="`/img/before_image/${card.befimgn}`" class="card-img-top" alt="..." />
                            <hr class="history-hr" />
                            <div class="history-div">
                              <table class="history-table">
                                <tr>
                                  <td class="history-td-1" colspan="3"><b>{{ card.title }}</b></td>
                                </tr>
                                <tr>
                                  <td colspan="2" class="history-td-2">{{ card.uploadText }}</td>
                                  <td style="text-align: left"><i class="bi bi-repeat"></i></td>
                                </tr>
                              </table>
                            </div>
                          </div>
                          <div class="flip-back">
                            <ul class="history-ul">
                              <b>REMINDER</b>
                              <i class="bi bi-trash3" @click.stop="delhistory(card.rnum)" style="position: absolute; right: 0; cursor: pointer;"></i>
                            </ul>
                            <img v-if="card.backImg !== 'defaultImg.jpeg'" :src="`/img/after_image/${card.backImg}`" class="card-img-top" alt="..." />
                            <img v-else @click="handleButtonClick" :src="`/img/MakeUp_image/upload2.png`" class="card-img-top" />
                            <hr class="history-hr" />
                            <div class="history-div">
                              <table class="history-table">
                                <tr>
                                  <td class="history-td-1" colspan="3"><b>{{ card.resultTitle }}</b></td>
                                </tr>
                                <tr>
                                  <td colspan="2" class="history-td-2">{{ card.resultText }}</td>
                                  <td style="text-align: left">
                                    <img :src="`/img/MyPage_image/${card.colorImg}`" alt="" style="width: 80px; height: 40px" />
                                  </td>
                                </tr>
                              </table>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </table>
      </div>
      <nav aria-label="Page navigation" class="pageblock">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
          </li>
          <li v-for="page in pageNumbers" :key="page" class="page-item" :class="{ active: page === currentPage }">
            <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Next</a>
          </li>
        </ul>
      </nav>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout.vue";
import axios from "axios";

export default {
  components: {
    MainLayout,
  },
  data() {
    return {
      detail: {},
      cards: [],
      currentPage: 1,
      itemsPerPage: 4,
      pageBlockSize: 10,
      mnum: 0,
      rnum: 0,
      befimgn: "",
    };
  },
  mounted() {
    this.mnum = this.$route.params.mnum;
    this.fetchData(this.mnum);
  },
  computed: {
    totalPages() {
      return Math.ceil(this.cards.length / this.itemsPerPage);
    },
    paginatedCards() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.cards.slice(start, end);
    },
    pageNumbers() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageBlockSize) * this.pageBlockSize + 1;
      const endPage = Math.min(startPage + this.pageBlockSize - 1, this.totalPages);
      const pages = [];
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
      return pages;
    }
  },
  methods: {
    flipCard(index) {
      const actualIndex = (this.currentPage - 1) * this.itemsPerPage + index;
      this.cards[actualIndex].isFlipped = !this.cards[actualIndex].isFlipped;
      this.rnum = this.cards[actualIndex].rnum;
      this.befimgn = this.cards[actualIndex].befimgn;
    },
    fetchData(mnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/detail?mnum=${mnum}`)
        .then((resp) => {
          this.detail = resp.data;
        })
        .catch((err) => {
          console.error(err);
        });

      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/myhistory`, {
        params: { mnum: mnum }
      })
        .then((response) => {
          this.cards = response.data.map(record => ({
            title: 'AI 퍼스널 컬러 진단',
            uploadText: '내가 업로드한 이미지',
            resultTitle: 'AI 퍼스널 컬러 진단 결과',
            date: new Date(record.rdate).toISOString().split('T')[0],
            befimgn: record.befimgn,
            backImg: record.afimgn,
            resultText: '나의 퍼스널 컬러: ' + this.getPersonalColor(record.season),
            colorImg: this.getColorImage(record.season),
            rnum: record.rnum,
          }));
        })
        .catch((error) => {
          console.error('Error fetching card data:', error);
        });
    },
    getPersonalColor(season) {
      const colorMapping = {
        'spring': '봄 웜톤',
        'summer': '여름 쿨톤',
        'fall': '가을 웜톤',
        'winter': '겨울 쿨톤',
        '봄': '봄 웜톤',
        '여름': '여름 쿨톤',
        '가을': '가을 웜톤',
        '겨울': '겨울 쿨톤'
      };
      return colorMapping[season] || '알 수 없음';
    },
    getColorImage(season) {
      const imageMapping = {
        'spring': 'springwarm.png',
        'summer': 'summercool.png',
        'fall': 'autumnwarm.png',
        'winter': 'wintercool.png',
        '봄': 'springwarm.png',
        '여름': 'summercool.png',
        '가을': 'autumnwarm.png',
        '겨울': 'wintercool.png'
      };
      return imageMapping[season] || 'noimage.png';
    },
    changePage(page) {
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    delhistory(rnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/delHistory`, {
        params: { rnum: rnum, mnum: this.mnum }
      })
        .then(() => {
          this.fetchData(this.mnum);
        })
        .catch((error) => {
          console.error('Error deleting history:', error);
        });
    },
    delAccount(mnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/delAccount`, {
        params: { mnum }
      })
        .then(() => {
          this.$router.push('/');
        })
        .catch((error) => {
          console.error('Error deleting member:', error);
        });
    },
    handleButtonClick() {
  const { mnum, mgender, season } = this.detail;
  const { befimgn, rnum } = this;

  console.log('Navigating to MakeUp with params:', { befimgn, mnum, mgender, season, rnum });

  if (befimgn && rnum) {
    this.$router.push({ name: 'MakeUp', params: { befimgn, mnum, mgender, season, rnum } });
  } else {
    console.error('befimgn or rnum is missing');
  }
}
  }
}
</script>


<style scoped>
img {
  height: 280px;

}
.col {
  margin: auto;
}
.flip {
  cursor: pointer;
  perspective: 1000px;
}
.flip-inner {
  transition: transform 0.6s;
  transform-style: preserve-3d;
}
.flip-inner.flipped {
  transform: rotateY(180deg);
}
.flip-front, .flip-back {
  backface-visibility: hidden;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.flip-back {
  transform: rotateY(180deg);
}
.pageblock{
  /* width: 100%; */
  text-align: center;
  margin-left: 25%;

}
</style>
