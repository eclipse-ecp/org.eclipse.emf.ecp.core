if [ $# -lt 3 ]; then
	exit
fi
AUT_DIR="$1/aut"
rm -rf ${AUT_DIR}/*
mkdir -p ${AUT_DIR}
cd ${AUT_DIR}
wget -q -O $2_$3.tar.gz https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/$2/$3/eclipse-modeling-$2-$3-linux-gtk-x86_64.tar.gz --proxy=off