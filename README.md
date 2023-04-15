```main:```

![badge](https://github.com/skwita/Blockchain-Networks/actions/workflows/maven.yml/badge.svg?branch=main)

```develop:```

![badge](https://github.com/skwita/Blockchain-Networks/actions/workflows/maven.yml/badge.svg?branch=develop)
# Blockchain-Networks
## Блокчейн


Блокчейн -- это распределённая база данных, где каждый участник может хранить, просматривать, проверять но не удалять данные. Все данные разбиваются на блоки, и каждый блок имеет односвязную связность с предыдущими блоками, позволяющую верифицировать эти блоки. Верификацией занимаются как раз узлы, которые и поддерживают работоспособность блокчейна.

Каждый блок состоит из следующих полей:
- index -- номер блока. Номера возрастают по порядку с первого.
- prev_hash -- хеш предыдущего блока.
- hash -- хеш текущего блока. Его нужно будет вычислить.
- data -- данные... В нашем случае пусть это будет строка длинной случайных 256 символов.
- nonce -- это дополнение, которое нужно будет сделать в блоке таким образом, чтобы выполнялось требование по хешированию.

Производство блока это простая операция.
Нужно сконкатенировать поля index, prev_hash, data и nonce, и результат записать в поле hash.
Но результат можно записывать только при условии, что hash заканчивается на 4 ноля. Если это не так, то нужно увеличить дополнение (nonce) и снова попробовать вычислить хеш (в качестве хеш-функции предлагаю использовать sha256, но окончательный выбор оставляю за вами).

Как только новый блок сгенерирован, то нужно отправить его остальным узлам (нодам) сети, и переходить к генерации следующего блока (т.е. index увеличивается, data нужно сгенерировать по новой, а в качестве prev_hash использовать хеш предыдущего блока). В то же время, пока мы подбираем хеш, кто-то из соседних узлов мог уже добиться успеха в этом, и присылает нам свой блок. Тогда нужно проверить, что хеш посчитан правильно, и переходить к генерации нового блока используя полученный в качестве предыдущего (если хеш не правильный, или блок произведён от уже устаревшего блока -- его нужно игнорировать).

Так же нужно следить, чтобы нода не оказалась в minority. Такое возможно, если она пропустила какой-то блок, и пытается генерировать новые блоки от уже устаревшего блока. В это время остальные ноды и блокчейн ушли уже вперёд. Чтобы разрешить эту ситуацию, можно попросить блоки у своих соседней, проверить их цепочки и использовать "новый" последний блок для продолжения работы по генерации блоков.

## Результат работы
```
firstContainer |[ind:0
hash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
prevHash:0
nonce:56708
data:0eDMaA7oOugYyJ33DtzsK1FWgvUqxIdFLvzWCYHKONRIS7HQXjL0upBLrbOvdVG9LiTau1mptEFZTqp7EtfmI8l0UdW8wuUcUX7a24AI0jfTtYxtJSirdjF5fGjp5QyqAnZxFSLPokxgwAr7HEXF0Uke1XCPCujYKwSglRqV05nfVUBy3eh9AJ74on4PMK7IKjMUqtf1bfANI2sY0HDAszq8H508wpT95u6eapXBX2pvVQ9L2ScMxyLS1I5yUjUM
timeStamp:2023-04-15 18:53:01.557
]
secondContainer |[ind:0
hash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
prevHash:0
nonce:56708
data:0eDMaA7oOugYyJ33DtzsK1FWgvUqxIdFLvzWCYHKONRIS7HQXjL0upBLrbOvdVG9LiTau1mptEFZTqp7EtfmI8l0UdW8wuUcUX7a24AI0jfTtYxtJSirdjF5fGjp5QyqAnZxFSLPokxgwAr7HEXF0Uke1XCPCujYKwSglRqV05nfVUBy3eh9AJ74on4PMK7IKjMUqtf1bfANI2sY0HDAszq8H508wpT95u6eapXBX2pvVQ9L2ScMxyLS1I5yUjUM
timeStamp:2023-04-15 18:53:01.557
]
thirdContainer |[ind:0
hash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
prevHash:0
nonce:56708
data:0eDMaA7oOugYyJ33DtzsK1FWgvUqxIdFLvzWCYHKONRIS7HQXjL0upBLrbOvdVG9LiTau1mptEFZTqp7EtfmI8l0UdW8wuUcUX7a24AI0jfTtYxtJSirdjF5fGjp5QyqAnZxFSLPokxgwAr7HEXF0Uke1XCPCujYKwSglRqV05nfVUBy3eh9AJ74on4PMK7IKjMUqtf1bfANI2sY0HDAszq8H508wpT95u6eapXBX2pvVQ9L2ScMxyLS1I5yUjUM
timeStamp:2023-04-15 18:53:01.557
]
firstContainer |[ind:0
hash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
prevHash:0
nonce:56708
data:0eDMaA7oOugYyJ33DtzsK1FWgvUqxIdFLvzWCYHKONRIS7HQXjL0upBLrbOvdVG9LiTau1mptEFZTqp7EtfmI8l0UdW8wuUcUX7a24AI0jfTtYxtJSirdjF5fGjp5QyqAnZxFSLPokxgwAr7HEXF0Uke1XCPCujYKwSglRqV05nfVUBy3eh9AJ74on4PMK7IKjMUqtf1bfANI2sY0HDAszq8H508wpT95u6eapXBX2pvVQ9L2ScMxyLS1I5yUjUM
timeStamp:2023-04-15 18:53:01.557
, ind:1
hash:b222632791153ede2590e1d5723a329d3dfa0545345506a85a25575a8f8f0000
prevHash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
nonce:386192209
data:H9AxIUpbLr2jFs6m81gB77HmOtaR0Y3eFHtxtgm3HfpfdYvMLT8Fq5gkP9HHRbBzsdZVGrlgNzVKFjDTfU6s35KXqvt37HZmqWY47IrtjgxnXPF1jvKaY4EHHYJuryLhc7aS4hwCMzLVuIerXfVxpZHpr004PsUuarFVh0Q35tdUnt8YHSdtQTlAMqtsqeNE86jaAHYJyn52f21RoBfeiDVCIDJ6jzqq1MMuAnSA2kBNWm1aXVZtZnaMWXl2LWGu
timeStamp:2023-04-15 18:53:01.663
]
secondContainer |[ind:0
hash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
prevHash:0
nonce:56708
data:0eDMaA7oOugYyJ33DtzsK1FWgvUqxIdFLvzWCYHKONRIS7HQXjL0upBLrbOvdVG9LiTau1mptEFZTqp7EtfmI8l0UdW8wuUcUX7a24AI0jfTtYxtJSirdjF5fGjp5QyqAnZxFSLPokxgwAr7HEXF0Uke1XCPCujYKwSglRqV05nfVUBy3eh9AJ74on4PMK7IKjMUqtf1bfANI2sY0HDAszq8H508wpT95u6eapXBX2pvVQ9L2ScMxyLS1I5yUjUM
timeStamp:2023-04-15 18:53:01.557
, ind:1
hash:b222632791153ede2590e1d5723a329d3dfa0545345506a85a25575a8f8f0000
prevHash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
nonce:386192209
data:H9AxIUpbLr2jFs6m81gB77HmOtaR0Y3eFHtxtgm3HfpfdYvMLT8Fq5gkP9HHRbBzsdZVGrlgNzVKFjDTfU6s35KXqvt37HZmqWY47IrtjgxnXPF1jvKaY4EHHYJuryLhc7aS4hwCMzLVuIerXfVxpZHpr004PsUuarFVh0Q35tdUnt8YHSdtQTlAMqtsqeNE86jaAHYJyn52f21RoBfeiDVCIDJ6jzqq1MMuAnSA2kBNWm1aXVZtZnaMWXl2LWGu
timeStamp:2023-04-15 18:53:01.663
]
thirdContainer |[ind:0
hash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
prevHash:0
nonce:56708
data:0eDMaA7oOugYyJ33DtzsK1FWgvUqxIdFLvzWCYHKONRIS7HQXjL0upBLrbOvdVG9LiTau1mptEFZTqp7EtfmI8l0UdW8wuUcUX7a24AI0jfTtYxtJSirdjF5fGjp5QyqAnZxFSLPokxgwAr7HEXF0Uke1XCPCujYKwSglRqV05nfVUBy3eh9AJ74on4PMK7IKjMUqtf1bfANI2sY0HDAszq8H508wpT95u6eapXBX2pvVQ9L2ScMxyLS1I5yUjUM
timeStamp:2023-04-15 18:53:01.557
, ind:1
hash:b222632791153ede2590e1d5723a329d3dfa0545345506a85a25575a8f8f0000
prevHash:2c9e1f5547ed3ca033cbb29c256c7a6e6cc39ca13847f9291ae8c449f7080000
nonce:386192209
data:H9AxIUpbLr2jFs6m81gB77HmOtaR0Y3eFHtxtgm3HfpfdYvMLT8Fq5gkP9HHRbBzsdZVGrlgNzVKFjDTfU6s35KXqvt37HZmqWY47IrtjgxnXPF1jvKaY4EHHYJuryLhc7aS4hwCMzLVuIerXfVxpZHpr004PsUuarFVh0Q35tdUnt8YHSdtQTlAMqtsqeNE86jaAHYJyn52f21RoBfeiDVCIDJ6jzqq1MMuAnSA2kBNWm1aXVZtZnaMWXl2LWGu
timeStamp:2023-04-15 18:53:01.663
]
```