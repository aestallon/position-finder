<script setup lang="ts">
import { clientApi } from '@/services';
import { ref } from 'vue';


const emit = defineEmits<{
  (event: 'complete', clientName: string): void;
}>();
const errText = ref('');

const name = ref('');
const email = ref('');

const nameRules: any[] = [(it: any) => (!!it && it.length > 0 && it.length < 101) || "Name with length between 1 and 100 required!"];
const mailRules: any[] = [(it: any) => /^\S+@\S+\.\S+$/.test(it) || "Valid e-mail address is required"];

const valid = ref<boolean | null>(null);

async function onBtnClicked(event: any) {
  event.preventDefault();

  clientApi.createClient({ name: name.value, email: email.value })
    .then((ok) => {
      if (ok.data.apiKey) {
        errText.value = '';
        localStorage.setItem("api-key", ok.data.apiKey);
        emit('complete', name.value);
      } else {
        errText.value = 'Unknown error';
      }
    }, (err) => {
      errText.value = err;
    });
}
</script>

<template>
  <v-card variant="elevated" rounded class="client-card">
    <v-form fast-fail v-model="valid" @submit.prevent="onBtnClicked" class="client-form">
      <v-text-field type="text" id="name" v-model="name" :rules="nameRules" label="Client Name"
        variant="outlined"></v-text-field>
      <v-text-field type="email" id="email" v-model="email" :rules="mailRules" label="Email"
        variant="outlined"></v-text-field>
      <v-btn color="primary" type="submit" :disabled="!valid">Create</v-btn>
      <v-label v-if="!!errText">{{ errText }}</v-label>
    </v-form>
  </v-card>
</template>

<style scoped>
.client-card {
  padding: 1rem;
}
</style>
