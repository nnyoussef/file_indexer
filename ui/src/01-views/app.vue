<script setup lang="ts">

import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";

const router = useRouter();
const tabContainer = ref(null)
onMounted(() => {
  router.push({path: '/create_index'}).then(() => {
    tabClicked(1);
  })
})

const tabClicked = (selectedElPosition: number) => {

  const selectedEl = tabContainer.value.children[selectedElPosition];
  const selectedElRouterLink = selectedEl.getAttribute('data-router-link');

  requestIdleCallback(() => {
    const previousSelectedElPosition = tabContainer.value.getAttribute('data-selected-index');

    if (Number(previousSelectedElPosition) === Number(selectedElPosition))
      return;
    selectedEl.setAttribute('data-selected', '');
    tabContainer.value.children[previousSelectedElPosition].removeAttribute('data-selected')
    tabContainer.value.setAttribute('data-selected-index', String(selectedElPosition));
  });
  router.push({path: selectedElRouterLink, replace: true});
}

</script>

<template>
  <div style="display: flex;flex-direction: column;height: 100%;min-height: 0;width: 100%">
    <div style="display: flex;align-items: center;">
      <img style="margin-right: 24px" height="80" width="80" src="/logo.webp" alt="File Indexer">
      <img style="margin-right: 24px" height="104" width="90" src="/lux.webp" alt="File Indexer">
      <div style="height: 70px;width: 1px;background: lightgray"></div>
      <div style="margin-left: 24px">
        <p style="font-family: monospace;font-size: 2.2em;margin: 0">File Indexer V0.0.1</p>
        <p style="font-family: monospace;font-size: 1.2em;margin: 0;font-style: italic">Made in Lëtzebuerg by Nassim Niclas Youssef</p>
      </div>
    </div>
    <link rel="stylesheet"
          as="style"
          async
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&amp;display=swap">

    <div ref="tabContainer"
         class="tabContainer"
         data-selected-index="0">
      <div data-router-link="/dashboard" data-selected @click="tabClicked(0)" class="tab">
        <span class="material-symbols-outlined">dashboard</span>
        <p>Dashboard</p>
      </div>
      <div data-router-link="/create_index" @click="tabClicked(1)" class="tab">
        <span class="material-symbols-outlined">create_new_folder</span>
        <p>Create Index </p>
      </div>
      <div data-router-link="/upload_file" @click="tabClicked(2)" class="tab">
        <span class="material-symbols-outlined">upload_file</span>
        <p>Upload File</p>
      </div>
      <div data-router-link="/search" @click="tabClicked(3)" class="tab">
        <span class="material-symbols-outlined">search</span>
        <p>Search</p>
      </div>
    </div>
    <div style="flex-grow: 1;margin-top: 16px;min-height: 0">
      <RouterView/>
    </div>
  </div>

</template>

<style scoped>

.tabContainer {
  height: 35px;
  margin-top: 24px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-bottom: 1px solid lightgray
}

.tab {
  height: calc(100% - 3px);
  display: flex;
  align-items: center;
  border-top: 3px solid lightgray;
  border-left: 1px solid lightgray;
  background: whitesmoke;
}

.tab:last-child {
  border-right: 1px solid lightgray;
  border-top-right-radius: 5px;
}

.tab:first-child {
  border-top-left-radius: 5px;
}

.tab > p {
  font-family: monospace;
  font-size: 11pt;
  margin: 0 0 0 8px;
  padding-right: 16px;
  font-weight: bold;
}

.tab > span {
  font-size: 14pt;
  padding-left: 16px;
  font-weight: normal;
  color: midnightblue;
}
.tab > img {
  padding-left: 16px;
}

.tab:hover {
  border-top-color: midnightblue;
  cursor: pointer;
  border-bottom: 1px solid whitesmoke;
  margin-top: 1px;
}

.tab[data-selected] {
  background: white;
  border-top-color: midnightblue;
  cursor: pointer;
  border-bottom: 1px solid white;
  margin-top: 1px;
}
</style>