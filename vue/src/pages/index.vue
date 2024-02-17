<template>
  <div class="container">
    <h1 class="title">
      Position Finder
    </h1>
    <application-client />
    <position-search />
  </div>
</template>

<script lang="ts" setup>
import ApplicationClient from '@/components/ApplicationClient.vue';
import PositionSearch from '@/components/PositionSearch.vue';

//
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  max-width: 80vw;
  margin-inline: auto;
}
.title {
  margin-inline: auto;
  margin-top: 5rem;
  margin-bottom: 5rem;
  font-weight: 700;
}
</style>
