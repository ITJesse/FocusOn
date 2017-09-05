const yaml = require('js-yaml');
const fs = require('fs-extra');

let oldTrans;
try {
  const old = fs.readFileSync('../src/main/resources/config.yml', "utf8");
  oldTrans = yaml.safeLoad(old);
} catch (error) {
  return console.log(error);
}

const newTrans = {
  entity: {},
  reason: {},
};

let newLables;
try {
  newLables = fs.readFileSync('./entity.txt', "utf8").split('\r\n');
} catch (error) {
  return console.log(error);
}

for (let label of newLables) {
  newTrans.entity[label] = oldTrans.entity[label] ? oldTrans.entity[label] : '';
}

try {
  newLables = fs.readFileSync('./reason.txt', "utf8").split('\r\n');
} catch (error) {
  return console.log(error);
}

for (let label of newLables) {
  newTrans.reason[label] = oldTrans.reason[label] ? oldTrans.reason[label] : '';
}

try {
  const newYaml = yaml.safeDump(newTrans);
  fs.writeFileSync('../src/main/resources/config.yml', newYaml, "utf-8");
} catch (error) {
  return console.log(error);
}