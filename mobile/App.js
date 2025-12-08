import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { StatusBar } from 'expo-status-bar';
import Ionicons from 'react-native-vector-icons/Ionicons'; // Iconite frumoase

// Importam ecranele noastre
import SleepScreen from './screens/SleepScreen';
import WorkoutScreen from './screens/WorkoutScreen';
import StepsScreen from './screens/StepsScreen';

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          headerShown: false, // Ascundem bara de sus standard (avem titlurile noastre)
          tabBarIcon: ({ focused, color, size }) => {
            let iconName;

            if (route.name === 'Somn') {
              iconName = focused ? 'moon' : 'moon-outline';
            } else if (route.name === 'Sport') {
              iconName = focused ? 'barbell' : 'barbell-outline';
            } else if (route.name === 'Pasi') {
              iconName = focused ? 'walk' : 'walk-outline';
            }

            return <Ionicons name={iconName} size={size} color={color} />;
          },
          tabBarActiveTintColor: '#007AFF', // Culoarea cand e selectat (Albastru)
          tabBarInactiveTintColor: 'gray',
        })}
      >
        <Tab.Screen name="Somn" component={SleepScreen} />
        <Tab.Screen name="Sport" component={WorkoutScreen} />
        <Tab.Screen name="Pasi" component={StepsScreen} />
      </Tab.Navigator>
      <StatusBar style="auto" />
    </NavigationContainer>
  );
}