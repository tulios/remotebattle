echo "Compilando remoteBattle..."
rm -rf compilado
ant
echo "compilado!"
echo "Gerando RMI dos objetos remotos!"
cp scripts/* compilado/
cd compilado
rmic br.remotebattle.remote.implementacao.JogoRemoto
rmic br.remotebattle.remote.implementacao.ServicoJogos
echo "Objetos gerados!"
DIRETORIO_LOCAL=$(pwd)
cd /tmp/
echo "diretorio: " $(pwd)
echo "Levantando o rmiregistry"
rmiregistry &
echo "rmiregistry no ar!"
echo "subindo servidor..."
cd $DIRETORIO_LOCAL
echo "diretorio: " $(pwd)
chmod 777 *.sh
./server-run.sh &
echo "servidor no ar!"
