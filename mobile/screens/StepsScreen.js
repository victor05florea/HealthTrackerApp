import { View, Text, StyleSheet } from 'react-native';

export default function StepsScreen() {
  return (
    <View style={styles.center}>
      <Text style={styles.text}>Aici vor fi Pașii 👣</Text>
      <Text style={{ marginTop: 10, color: 'gray' }}>(Urmează să implementăm)</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f7fa'
  },
  text: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#333'
  }
});