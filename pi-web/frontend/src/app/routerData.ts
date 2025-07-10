export interface CustomData {
  title: string;
}

const PRE_TITLE = 'pi server';

export function createTitle(data: CustomData) {
  if (data.title === '') {
    return PRE_TITLE;
  } else {
    return `${PRE_TITLE} | ${data.title}`;
  }
}
