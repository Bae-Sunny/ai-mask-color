<template>
    <MainLayout>
      <div class="all-padding"></div>
      <div id="content" class="container">
        <h2 class="text-center wow fadeInUp" style="margin-top: 30px; font-weight: bold;" data-wow-duration="3s" data-wow-delay="0.5s">
          퍼스널 컬러 자가진단
        </h2>
        <div class="test-contents">
          <div class="up-panel">
          <div class="image-upload">
            <div class="image-frame">
              <img
                src="/img/Test_image/testframe.png"
                alt="selfchk"
                class="existing-image" style="height: 100%;"
              />
              <div
                class="bottom-color"
                :style="{ backgroundColor: selectedColor }"
              ></div>
              <div v-if="imagePreview" class="preview-container">
                <img :src="imagePreview" alt="Preview" class="preview-image" style="height:100%;"/>
              </div>
              <button class="image-upload-btn" @click="openFileInput">
                사진 업로드
              </button>
            </div>
            <input
              type="file"
              ref="fileInput"
              @change="handleFileUpload"
              style="display: none"
              accept="image/*"
            />
          </div>
        </div>
        <div class="down-panel">
          <div class="color-palette">
            <table>
              <thead>
                <tr>
                  <th colspan="2">WARM TONE</th>
                  <th colspan="2">COOL TONE</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="tone-title" colspan="2">SPRING</td>
                  <td class="tone-title" colspan="2">SUMMER</td>
                </tr>
                <tr>
                  <td colspan="2" class="spring_warm">
                    <div class="color-swatch" v-for="color in springWarmColors" :style="{ backgroundColor: color }" @click="selectColor(color)"></div>
                  </td>
                  <td colspan="2" class="summer_cool">
                    <div class="color-swatch" v-for="color in summerCoolColors" :style="{ backgroundColor: color }" @click="selectColor(color)"></div>
                  </td>
                </tr>
                <tr>
                  <td class="tone-title" colspan="2">AUTUMN</td>
                  <td class="tone-title" colspan="2">WINTER</td>
                </tr>
                <tr>
                  <td colspan="2" class="autumn_warm">
                    <div class="color-swatch" v-for="color in autumnWarmColors" :style="{ backgroundColor: color }" @click="selectColor(color)"></div>
                  </td>
                  <td colspan="2" class="winter_cool">
                    <div class="color-swatch" v-for="color in winterCoolColors" :style="{ backgroundColor: color }" @click="selectColor(color)"></div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>      

      </div>
    </div>
  </MainLayout>
  </template>
  
  <script>
  import MainLayout from "@/layouts/MainLayout.vue";
  
  export default {
  components: {
    MainLayout,
  },
  data() {
    return {
      imagePreview: null,
      selectedColor: "#FFFFFF",
      springWarmColors: [
        "#FE9901",
        "#FE804D",
        "#FE592B",
        "#F91D41",
        "#29AAAF",
        "#860EC5",
        "#B0DF73",
        "#8DCD76",
        "#67BD64",
        "#B3E1EE",
        "#67C2B8",
        "#41B198",
        "#EE657B",
        "#EB89B8",
        "#F1E2E5",
        "#F2EED1",
        "#F2ED89",
        "#F1EB0F",
        "#F64490",
        "#D8CB97",
        "#D8B346",
        "#C0AC6F",
        "#4D1A05",
        "#0A286E",
      ],
      summerCoolColors: [
        "#F95C93",
        "#B2D5E8",
        "#9AC4DD",
        "#8E8BC1",
        "#8B56A6",
        "#CCC3D9",
        "#F1D5E9",
        "#EDA4D1",
        "#76A3CC",
        "#5184BB",
        "#535FA9",
        "#6946A0",
        "#D9F0F6",
        "#B3E1CC",
        "#80CCBD",
        "#EA417F",
        "#E5E6AA",
        "#BEA1A6",
        "#A3BBBB",
        "#80A38F",
        "#7E8592",
        "#816F5B",
        "#805042",
        "#6E3738",
      ],
      autumnWarmColors: [
        "#412A0A",
        "#58360B",
        "#F87E78",
        "#FFF2DF",
        "#D8D9C9",
        "#CABC90",
        "#BE8840",
        "#745F10",
        "#757F4D",
        "#4D5E2A",
        "#253F12",
        "#358025",
        "#0C3E18",
        "#35001A",
        "#591C19",
        "#CCA107",
        "#CB7E0F",
        "#CB5003",
        "#C01600",
        "#F7544D",
        "#009377",
        "#80AEA3",
        "#013133",
        "#690B9D",
      ],
      winterCoolColors: [
        "#051536",
        "#0B286E",
        "#75C1DA",
        "#F1FAFD",
        "#F1FEAE",
        "#BEC4DE",
        "#2497DA",
        "#0000FE",
        "#29007B",
        "#850DC4",
        "#FDF200",
        "#0C8731",
        "#0E906C",
        "#005221",
        "#76003A",
        "#760C6E",
        "#D2003F",
        "#FE00FE",
        "#D69ECD",
        "#B1AAD3",
        "#CBCBCB",
        "#808080",
        "#4C4C4C",
        "#030119",
      ],
    };
  },
  methods: {
    openFileInput() {
      this.$refs.fileInput.click();
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imagePreview = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    selectColor(color) {
      this.selectedColor = color;
    },
  },
  };
  </script>
  
<style scoped>

</style>